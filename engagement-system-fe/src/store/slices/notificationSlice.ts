import { createSlice } from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";

export type NotificationType = "success" | "error" | "info" | "warning";

export interface Notification {
  id: string;
  type: NotificationType;
  message: string;
  duration?: number;
  title?: string;
  action?: {
    label: string;
    onClick: () => void;
  };
}

interface NotificationState {
  notifications: Notification[];
}

const initialState: NotificationState = {
  notifications: [],
};

const notificationSlice = createSlice({
  name: "notifications",
  initialState,
  reducers: {
    addNotification: (
      state,
      action: PayloadAction<Omit<Notification, "id">>
    ) => {
      const id = Math.random().toString(36).substring(7);
      const defaultDuration = action.payload.type === "error" ? 10000 : 5000;
      state.notifications.push({
        ...action.payload,
        id,
        duration: action.payload.duration || defaultDuration,
      });
    },
    removeNotification: (state, action: PayloadAction<string>) => {
      state.notifications = state.notifications.filter(
        (notification) => notification.id !== action.payload
      );
    },
    clearNotifications: (state) => {
      state.notifications = [];
    },
  },
});

export const { addNotification, removeNotification, clearNotifications } =
  notificationSlice.actions;

// Helper functions for creating notifications
export const showSuccess = (
  message: string,
  options?: {
    duration?: number;
    title?: string;
    action?: {
      label: string;
      onClick: () => void;
    };
  }
) =>
  addNotification({
    type: "success",
    message,
    ...options,
  });

export const showError = (
  message: string,
  options?: {
    duration?: number;
    title?: string;
    action?: {
      label: string;
      onClick: () => void;
    };
  }
) =>
  addNotification({
    type: "error",
    message,
    ...options,
  });

export const showInfo = (
  message: string,
  options?: {
    duration?: number;
    title?: string;
    action?: {
      label: string;
      onClick: () => void;
    };
  }
) =>
  addNotification({
    type: "info",
    message,
    ...options,
  });

export const showWarning = (
  message: string,
  options?: {
    duration?: number;
    title?: string;
    action?: {
      label: string;
      onClick: () => void;
    };
  }
) =>
  addNotification({
    type: "warning",
    message,
    ...options,
  });

export default notificationSlice.reducer;
