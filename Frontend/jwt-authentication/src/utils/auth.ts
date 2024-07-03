import { NextAuthOptions, getServerSession } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import apiAuthSignIn from "./api";
import { JWT } from "next-auth/jwt";

interface UserWithToken {
  id: string;
  email: string;
  accessToken: string;
}

export const authOptions: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        username: { label: "Username", type: "text" },
        email: { label: "Email", type: "email" },
        password: { label: "Password", type: "password" },
      },
      async authorize(credentials: Record<"email" | "username" | "password", string> | undefined): Promise<UserWithToken | null> {
        if (!credentials) {
          throw new Error("Invalid credentials");
        }
        const user = await apiAuthSignIn(credentials);
        if (user && !user.error) {
          return {
            id: user.id,
            email: user.email,
            accessToken: user.token,
          };
        } else {
          throw new Error(user?.error || "Authentication failed");
        }
      },
    }),
  ],
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        token.accessToken = (user as UserWithToken).accessToken;
      }
      return token as JWT;
    },
    async session({ session, token }) {
      if (token) {
        session.accessToken = token.accessToken as string;
      }
      return session;
    },
  },
  session: {
    strategy: "jwt",
    maxAge: 30 * 24 * 60 * 60,
  },
  pages: {
    signIn: "/auth/signin",
  },
  jwt: {
    secret: process.env.NEXT_JWT_SECRET as string,
  },
  secret: process.env.NEXTAUTH_SECRET as string,
};


export const getServerAuthSession = () => getServerSession(authOptions);
