import React, { useState, useEffect } from 'react';

const Alert = ({ message, type }) => {
    const [isVisible, setIsVisible] = useState(true);

    useEffect(() => {
        setIsVisible(true); // Show alert when message or type changes
    }, [message, type]);

    if (!isVisible || !message) {
        return null;
    }

    let alertClasses = 'p-4 mb-4 text-sm rounded-lg ';
    let textClasses = '';
    let closeButtonClasses = 'ml-auto -mx-1.5 -my-1.5 rounded-lg p-1.5 inline-flex h-8 w-8 ';

    switch (type) {
        case 'success':
            alertClasses += 'bg-green-100 text-green-800 dark:bg-green-200 dark:text-green-900';
            textClasses += 'text-green-800 dark:text-green-900';
            closeButtonClasses += 'bg-green-100 text-green-500 focus:ring-green-400 hover:bg-green-200 dark:bg-green-200 dark:text-green-600 dark:hover:bg-green-300';
            break;
        case 'error':
            alertClasses += 'bg-red-100 text-red-800 dark:bg-red-200 dark:text-red-900';
            textClasses += 'text-red-800 dark:text-red-900';
            closeButtonClasses += 'bg-red-100 text-red-500 focus:ring-red-400 hover:bg-red-200 dark:bg-red-200 dark:text-red-600 dark:hover:bg-red-300';
            break;
        case 'warning':
            alertClasses += 'bg-yellow-100 text-yellow-800 dark:bg-yellow-200 dark:text-yellow-900';
            textClasses += 'text-yellow-800 dark:text-yellow-900';
            closeButtonClasses += 'bg-yellow-100 text-yellow-500 focus:ring-yellow-400 hover:bg-yellow-200 dark:bg-yellow-200 dark:text-yellow-600 dark:hover:bg-yellow-300';
            break;
        case 'info':
        default:
            alertClasses += 'bg-blue-100 text-blue-800 dark:bg-blue-200 dark:text-blue-900';
            textClasses += 'text-blue-800 dark:text-blue-900';
            closeButtonClasses += 'bg-blue-100 text-blue-500 focus:ring-blue-400 hover:bg-blue-200 dark:bg-blue-200 dark:text-blue-600 dark:hover:bg-blue-300';
            break;
    }

    return (
        <div className={alertClasses} role="alert">
            <div className="flex items-center">
                <span className={`font-medium ${textClasses}`}>{message}</span>
                <button
                    type="button"
                    className={closeButtonClasses}
                    data-dismiss-target="#alert"
                    aria-label="Close"
                    onClick={() => setIsVisible(false)}
                >
                    <span className="sr-only">Dismiss</span>
                    <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                        <path
                            fillRule="evenodd"
                            d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                            clipRule="evenodd"
                        ></path>
                    </svg>
                </button>
            </div>
        </div>
    );
};

export default Alert;