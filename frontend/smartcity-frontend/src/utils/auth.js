// utils/auth.js

export const saveAuth = (token, userData) => {
  const payload = {
    id: userData.id,
    email: userData.email,
    name: userData.name,
    role: userData.role,
    token: token
  };

  localStorage.setItem("smartcity_auth", JSON.stringify(payload));
};

export const getUser = () => {
  try {
    const data = localStorage.getItem("smartcity_auth");
    if (!data) return null;
    return JSON.parse(data);
  } catch (err) {
    return null;
  }
};

export const getToken = () => {
  const user = getUser();
  return user ? user.token : null;
};

export const clearAuth = () => {
  localStorage.removeItem("smartcity_auth");
};
