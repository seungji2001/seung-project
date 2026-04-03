'use client';

// TODO 1: useEffect, useRouter, useAuth import 하세요
// import { ??? } from 'react';
// import { ??? } from 'next/navigation';
// import { ??? } from '../_context/CookieContext';

import {useRouter} from "next/navigation";
import {useAuth} from "@/app/_context/CookieContext";
import {useEffect} from "react";
import {useAlert} from "@/app/_context/AlertContext";
// TODO 6: useAlert import 하세요
// import { ??? } from '../_context/AlertContext';

export default function DashboardPage() {

    const router = useRouter();
    const { user_id, isAuthenticated, logout } = useAuth();
    const { showAlert } = useAlert();

    useEffect(() => {
        if (!isAuthenticated) {
            router.push('/login');
        }
    }, [isAuthenticated]);

    const handleLogout = () => {
        logout();
        // TODO 8: router.push 전에 showAlert('로그아웃 되었습니다.') 호출하세요
        showAlert('로그아웃 되었습니다.');
        router.push('/login');
    };

    return (
        <div className="min-h-screen bg-gray-50 flex items-center justify-center">
            <div className="bg-white rounded-2xl shadow-md p-10 w-full max-w-md text-center">
                <h1 className="text-2xl font-bold text-gray-800 mb-2">대시보드</h1>

                {/* TODO 5: user_id를 여기에 표시하세요 */}
                <p className="text-gray-500 mb-8">안녕하세요, ???님</p>

                <button
                    onClick={handleLogout}
                    className="bg-red-500 hover:bg-red-600 text-white font-semibold px-6 py-2.5 rounded-lg transition-colors"
                >
                    로그아웃
                </button>
            </div>
        </div>
    );
}