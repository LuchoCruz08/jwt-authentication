import { NEXT_PUBLIC_BACKEND_API } from "./constants";

export default async function apiAuthSignIn(credentials: { email: string; password: string }) {
    try {
        const response = await fetch(`${NEXT_PUBLIC_BACKEND_API}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(credentials),
        });

        if(!response.ok) {
            throw new Error("Invalid credentials");
        }

        const data = await response.json();

        if(data.error) {
            return { error: data.message };
        }

        return data;
    } catch(error) {
        return { error: (error as Error).message };
    }
}

export const BAPI = process.env.NEXT_PUBLIC_BACKEND_API as string;
export const Token = process.env.NEXT_PUBLIC_BEARER as string;