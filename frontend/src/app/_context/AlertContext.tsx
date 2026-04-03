'use client';

import { createContext, useContext, useState } from "react";

// Alert에 필요한 데이터
interface AlertContextType {
    showAlert: (message: string) => void;  // 알림 띄우는 함수
}

const AlertContext = createContext<AlertContextType | undefined>(undefined);

export function AlertProvider({ children }: { children: React.ReactNode }) {

    // TODO 1: 두 가지 상태를 선언하세요
    const [message, setMessage] = useState('');
    const [isOpen, setIsOpen] = useState(false);
    // - message: 표시할 텍스트 (string, 초기값 '')
    // - isOpen: 팝업이 열려있는지 (boolean, 초기값 false)

    const showAlert = (message: string) => {
        // TODO 2: message 상태 업데이트, isOpen을 true로
        setMessage(message);
        setIsOpen(true);
    };

    const closeAlert = () => {
        // TODO 3: isOpen을 false로, message를 ''로 초기화
        setMessage('');
        setIsOpen(false);
    };

    return (
        <AlertContext.Provider value={{ showAlert }}>
            {children}

            {/* TODO 4: isOpen이 true일 때만 이 팝업이 보이게 하세요 */}
            { isOpen && (
                <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
                    <div className="bg-white rounded-2xl shadow-xl p-8 w-80 text-center">
                        <p className="text-gray-800 font-medium mb-6">
                            { message }
                        </p>
                        <button
                            onClick={closeAlert}
                            className="bg-blue-500 hover:bg-blue-600 text-white font-semibold px-6 py-2 rounded-lg transition-colors"
                        >
                            확인
                        </button>
                    </div>
                </div>
             )}
        </AlertContext.Provider>
    );
}

export function useAlert() {
    const context = useContext(AlertContext);
    if (context === undefined) {
        throw new Error('useAlert must be used within AlertProvider');
    }
    return context;
}