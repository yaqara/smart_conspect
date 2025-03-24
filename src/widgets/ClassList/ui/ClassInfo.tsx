import { FC } from "react"


export const ClassInfo:FC=()=>{    
const testClass={
    name:'10Г Рофлы с Волк Стрит',
    studentscount:27
}    
    return(
            <div className="max-w-xs mx-auto bg-white shadow-lg rounded-lg overflow-hidden my-8 mx-10 hover:scale-105">
                <div className="bg-red-500 text-white text-xl font-bold py-2 px-4 text-center">
                    {testClass.name}
                </div>
                <div className="px-4 py-2 text-gray-700 text-base">
                    Кол-во учеников - {testClass.studentscount}
                </div>
            </div>
        
    )
}