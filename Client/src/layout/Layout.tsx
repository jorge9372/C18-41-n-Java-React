// Layout.tsx
import { Outlet } from "react-router-dom";
import MenuBarContainer from "@/components/Menubar/Menubar.container";
import SidebarContainer from "@/components/Sidebar/Sidebar.container";
import React, { ReactNode } from "react";

interface LayoutProps {
  children?: ReactNode;
}
const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <div className="flex">
      <MenuBarContainer />
      <div className="flex-grow px-[24px] py-[24px]">
        {children}
        <Outlet />
      </div>
      <SidebarContainer />
    </div>
  );
};

export default Layout;
