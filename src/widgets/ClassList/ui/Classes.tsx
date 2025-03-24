import { ClassInfo } from "./ClassInfo";
import { FC } from "react";

export const Classes:FC=()=>{
const widgets = Array.from({ length: 20 }, (_, i) => <ClassInfo key={i} />);
return(
    <div className="flex flex-wrap  justify-center">
      {widgets}
    </div>
)
}