import { url } from "inspector";
import { NextResponse } from "next/server";
import { NextFetchEvent, NextRequest } from "next/server";

export function middleware(req: NextRequest, event: NextFetchEvent) {
    try {
        const token = req.cookies.get("next-auth.session-token");

        if(!token?.value) {
            return NextResponse.redirect(new URL("/auth/login", req.url));
        }

        return NextResponse.next();
    } catch(error) {
        console.error("Authentication error: ", error);
        return NextResponse.json(
            { success: false, message: "An error occurred during authentication." },
            { status: 401 }
        );
    }
}

export const config = {
    matcher: "/dashboard/:path",
}