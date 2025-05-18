import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  CheckCircleIcon,
  ExclamationCircleIcon,
  InformationCircleIcon,
  XCircleIcon,
  XMarkIcon,
} from "@heroicons/react/24/outline";
import { removeNotification } from "../store/slices/notificationSlice";
import type { AppState } from "../store";

const Notification = () => {
  const dispatch = useDispatch();
  const notifications = useSelector(
    (state: AppState) => state.notifications.notifications
  );

  useEffect(() => {
    notifications.forEach((notification) => {
      if (notification.duration) {
        setTimeout(() => {
          dispatch(removeNotification(notification.id));
        }, notification.duration);
      }
    });
  }, [notifications, dispatch]);

  const getIcon = (type: string) => {
    switch (type) {
      case "success":
        return (
          <CheckCircleIcon
            className="h-6 w-6 text-green-400"
            aria-hidden="true"
          />
        );
      case "error":
        return (
          <XCircleIcon className="h-6 w-6 text-red-400" aria-hidden="true" />
        );
      case "warning":
        return (
          <ExclamationCircleIcon
            className="h-6 w-6 text-yellow-400"
            aria-hidden="true"
          />
        );
      case "info":
        return (
          <InformationCircleIcon
            className="h-6 w-6 text-blue-400"
            aria-hidden="true"
          />
        );
      default:
        return null;
    }
  };

  const getBackgroundColor = (type: string) => {
    switch (type) {
      case "success":
        return "bg-green-50 dark:bg-green-900/20";
      case "error":
        return "bg-red-50 dark:bg-red-900/20";
      case "warning":
        return "bg-yellow-50 dark:bg-yellow-900/20";
      case "info":
        return "bg-blue-50 dark:bg-blue-900/20";
      default:
        return "bg-gray-50 dark:bg-gray-900/20";
    }
  };

  const getBorderColor = (type: string) => {
    switch (type) {
      case "success":
        return "border-green-400 dark:border-green-500";
      case "error":
        return "border-red-400 dark:border-red-500";
      case "warning":
        return "border-yellow-400 dark:border-yellow-500";
      case "info":
        return "border-blue-400 dark:border-blue-500";
      default:
        return "border-gray-400 dark:border-gray-500";
    }
  };

  const getTextColor = (type: string) => {
    switch (type) {
      case "success":
        return "text-green-700 dark:text-green-200";
      case "error":
        return "text-red-700 dark:text-red-200";
      case "warning":
        return "text-yellow-700 dark:text-yellow-200";
      case "info":
        return "text-blue-700 dark:text-blue-200";
      default:
        return "text-gray-700 dark:text-gray-200";
    }
  };

  if (notifications.length === 0) return null;

  return (
    <div
      className="fixed top-4 right-4 z-50 space-y-4"
      role="region"
      aria-label="Notifications"
    >
      {notifications.map((notification) => (
        <div
          key={notification.id}
          className={`${getBackgroundColor(
            notification.type
          )} border-l-4 ${getBorderColor(
            notification.type
          )} p-4 rounded-md shadow-lg max-w-md transform transition-all duration-300 ease-in-out hover:scale-[1.02]`}
          role="alert"
          aria-live="polite"
        >
          <div className="flex items-start">
            <div className="flex-shrink-0" aria-hidden="true">
              {getIcon(notification.type)}
            </div>
            <div className="ml-3 w-0 flex-1">
              {notification.title && (
                <p
                  className={`text-sm font-semibold ${getTextColor(
                    notification.type
                  )}`}
                >
                  {notification.title}
                </p>
              )}
              <p
                className={`text-sm ${
                  notification.title ? "mt-1" : ""
                } ${getTextColor(notification.type)}`}
              >
                {notification.message}
              </p>
              {notification.action && (
                <div className="mt-3">
                  <button
                    type="button"
                    className={`text-sm font-medium ${getTextColor(
                      notification.type
                    )} hover:underline focus:outline-none focus:underline`}
                    onClick={() => {
                      notification.action?.onClick();
                      dispatch(removeNotification(notification.id));
                    }}
                  >
                    {notification.action.label}
                  </button>
                </div>
              )}
            </div>
            <div className="ml-4 flex-shrink-0 flex">
              <button
                type="button"
                className="inline-flex text-gray-400 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-400 rounded-md"
                onClick={() => dispatch(removeNotification(notification.id))}
                aria-label="Close notification"
              >
                <span className="sr-only">Close notification</span>
                <XMarkIcon className="h-5 w-5" aria-hidden="true" />
              </button>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Notification;
