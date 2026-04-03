export interface User {
    id: number;
    username: string;
    token: string;
}

export interface LoginCredentials {
    username: string;
    password: string;
}

export interface LoginResponse {
    success: boolean;
    message?: string;
    user?: User;
}