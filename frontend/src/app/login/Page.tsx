'use client';

import { useState } from "react";
import { useRouter } from "next/navigation";
import {useAuth} from "@/app/_context/CookieContext";
import {useAlert} from "@/app/_context/AlertContext";

export default function LoginPage() {

    const [user_id, setUser_id] = useState('');
    const [password, setPassword] = useState('');
    const router = useRouter();
    const {login} = useAuth();
    const {showAlert} = useAlert();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (user_id === 'admin' && password === '1234') {
            login(user_id, 'fake-token');
            router.push('/dashboard');
        } else {
            showAlert('아이디 또는 비밀번호가 틀렸습니다.')
        }
    };

    return (
        <div className="min-h-screen bg-gray-50 flex items-center justify-center">
            <div className="bg-white rounded-2xl shadow-md p-10 w-full max-w-md">
                <h1 className="text-2xl font-bold text-gray-800 mb-8 text-center">로그인</h1>

                <form onSubmit={handleSubmit} className="flex flex-col gap-5">
                    <div>
                        <label className="block text-sm font-medium text-gray-600 mb-1">
                            아이디
                        </label>
                        <input
                            type="text"
                            placeholder="아이디를 입력하세요"
                            value={user_id}
                            onChange={(e) => setUser_id(e.target.value)}
                            className="w-full border border-gray-300 rounded-lg px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-600 mb-1">
                            비밀번호
                        </label>
                        <input
                            type="password"
                            placeholder="비밀번호를 입력하세요"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="w-full border border-gray-300 rounded-lg px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
                        />
                    </div>

                    <button
                        type="submit"
                        className="w-full bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2.5 rounded-lg transition-colors"
                    >
                        로그인
                    </button>
                </form>
            </div>
        </div>
    );
}