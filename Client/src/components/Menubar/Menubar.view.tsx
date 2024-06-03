// Menubar.view.tsx
import React from "react";
import { NavLink } from "react-router-dom";
import {
  Home,
  UserRound,
  Users,
  FolderHeart,
  CalendarCheck,
  BookmarkCheck,
  Globe,
  ArrowRight,
} from "lucide-react";
import { Button } from "@/components/ui";

const MenubarView: React.FC = () => {
  
  const menuItems = [
    { name: "Inicio", path: "/inicio", icon: <Home /> },
    { name: "Estudiantes", path: "/estudiantes", icon: <UserRound /> },
    { name: "Tutores", path: "/tutores", icon: <Users /> },
    { name: "Explorar", path: "/explorar", icon: <Globe /> },
    { name: "Recursos", path: "/recursos", icon: <FolderHeart /> },
    { name: "Guardado", path: "/guardado", icon: <BookmarkCheck /> },
    { name: "Evaluaciones", path: "/evaluaciones", icon: <CalendarCheck /> },
  ];

  return (
    <div className="fixed top-[24px] bottom-[24px] left-[24px] flex flex-col lg:w-[226px] xl:w-[316px] 2xl:w-[316px]">
      <h1 className="text-3xl font-bold tracking-wide bg-transparent mb-[24px]">
        <span className="text-accent">Swap</span> It Up
      </h1>
      <div className="bg-accent text-black p-4 rounded-lg flex flex-col h-full overflow-y-auto">
        <ul className="space-y-4">
          {menuItems.map((item, index) => (
            <li key={index}>
              <NavLink
                to={item.path}
                className={({ isActive }) =>
                  `flex items-center space-x-2 p-2 rounded-lg ${
                    isActive
                      ? 'bg-accent-foreground text-white'
                      : 'text-black hover:bg-accent-foreground hover:text-white'
                  }`
                }
              >
                {item.icon}
                <span className="ml-4 text-base leading-6 font-medium">{item.name}</span>
              </NavLink>
            </li>
          ))}
        </ul>
        <div className="mt-auto">
          <p className="text-black hover:underline">
            Obtén más puntos para canjear en clases y mucho más
          </p>
          <Button
            variant="default"
            className="bg-accent-foreground rounded-lg mt-2"
          >
            Actualizar <ArrowRight className="ml-2 h-6 w-6" />
          </Button>
        </div>
      </div>
    </div>
  );
};

export default MenubarView;
