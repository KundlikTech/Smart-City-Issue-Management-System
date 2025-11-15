export const getNotifications = () => {
  const n = localStorage.getItem("notifications");
  return n ? JSON.parse(n) : [];
};

export const addNotification = (data) => {
  const old = getNotifications();
  const updated = [data, ...old];
  localStorage.setItem("notifications", JSON.stringify(updated));
};

export const clearNotifications = () => {
  localStorage.removeItem("notifications");
};
