'use client';

// TODO 1: useEffect, useCallback 도 추가로 import 하세요
import {createContext, useCallback, useContext, useEffect, useState} from "react";

interface AuthContextType {
    user_id: string;
    isAuthenticated: boolean;
    isInitialized: boolean;  // 추가: 쿠키 확인 완료 여부
    login: (user_id: string, token: string) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {

    const [user_id, setUser_id] = useState('');
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [isInitialized, setIsInitialized] = useState(false); // 처음엔 false

    // TODO 2: 쿠키에서 특정 값을 읽는 헬퍼 함수를 만드세요
    // document.cookie 는 "user_id=admin; token=abc" 같은 문자열을 반환해요
    // 이 문자열에서 특정 키의 값만 꺼내야 해요
    const getCookie = (name: string): string => {
        const match = document.cookie
            .split('; ')                        // ["user_id=admin", "token=abc"]
            .find(row => row.startsWith(name + '='))  // "user_id=admin" 찾기
        return match ? match.split('=')[1] : '';       // "admin" 반환
    };

    // TODO 3: useCallback으로 refreshAuth 함수를 만드세요
    // 역할: 페이지 로드 시 쿠키에서 로그인 정보를 복원
    // - 쿠키에서 user_id, token 읽기 (getCookie 사용)
    // - 둘 다 있으면 → setUser_id, setIsAuthenticated(true)
    // - 없으면 → 상태 초기화
    // - 마지막엔 항상 → setIsInitialized(true)
    const refreshAuth = useCallback(() => {
        const savedUserId = getCookie('user_id');
        const savedToken  = getCookie('token');
        if (savedUserId && savedToken) {
            setUser_id(savedUserId);
            setIsAuthenticated(true);
        } else {
            setUser_id('');
            setIsAuthenticated(false);
        }
        setIsInitialized(true);
    }, []);

    // TODO 4: useEffect로 컴포넌트 첫 마운트 시 refreshAuth 호출하세요
    // - 의존성 배열에 refreshAuth 넣기
    useEffect(() => {
        refreshAuth();
    }, [refreshAuth]);

    const login = (user_id: string, token: string) => {
        document.cookie = `user_id=${user_id}; path=/`;
        document.cookie = `token=${token}; path=/`;
        setUser_id(user_id);
        setIsAuthenticated(true);
    };

    const logout = () => {
        document.cookie = `user_id=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/`;
        document.cookie = `token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/`;
        setUser_id('');
        setIsAuthenticated(false);
    };

    return (
        <AuthContext.Provider value={{ user_id, isAuthenticated, isInitialized, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (context === undefined) {
        throw new Error('useAuth must be used within AuthProvider');
    }
    return context;
}