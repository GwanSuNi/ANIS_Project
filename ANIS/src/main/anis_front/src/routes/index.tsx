import {useRoutes} from "react-router-dom";
import MainRoutes from "./MainRoutes";
import AuthenticationRoutes from "./AuthenticationRoutes";
import AdminRoutes from "./AdminRoutes";

export default function ThemeRoutes() {
    return useRoutes([...MainRoutes, AuthenticationRoutes, AdminRoutes]);
}