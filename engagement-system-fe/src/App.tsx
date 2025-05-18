import { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Notification from "./components/Notification";

// Layouts
import AuthLayout from "./layouts/AuthLayout";
import MainLayout from "./layouts/MainLayout";

// Pages
import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";
import Dashboard from "./pages/Dashboard";
import Complaints from "./pages/complaints/Complaints";
import ComplaintDetails from "./pages/complaints/ComplaintDetails";
import NewComplaint from "./pages/complaints/NewComplaint";
import Categories from "./pages/admin/Categories";
import Agencies from "./pages/admin/Agencies";
import Profile from "./pages/Profile";
import NotFound from "./pages/NotFound";

const AuthCheck = ({ children }: { children: React.ReactNode }) => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const token = localStorage.getItem("token");
    const isAuthPage =
      location.pathname === "/login" || location.pathname === "/register";

    if (!token && !isAuthPage) {
      navigate("/login");
    } else if (token && isAuthPage) {
      navigate("/");
    }
  }, [navigate, location]);

  return <>{children}</>;
};

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Notification />
        <AuthCheck>
          <Routes>
            {/* Auth Routes */}
            <Route element={<AuthLayout />}>
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
            </Route>

            {/* Protected Routes */}
            <Route element={<MainLayout />}>
              <Route path="/" element={<Dashboard />} />
              <Route path="/complaints" element={<Complaints />} />
              <Route path="/complaints/new" element={<NewComplaint />} />
              <Route path="/complaints/:id" element={<ComplaintDetails />} />
              <Route path="/categories" element={<Categories />} />
              <Route path="/agencies" element={<Agencies />} />
              <Route path="/profile" element={<Profile />} />
            </Route>

            {/* 404 Route */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </AuthCheck>
      </div>
    </Router>
  );
}

export default App;
