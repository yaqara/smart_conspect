'use client'
import {FC} from 'react';
import {Header} from '~/features/Header';
import { PageTitle } from '~/features/PageTitle';
import  {AddHWForm}  from '~/widgets/AddHW/ui/AddHWForm';
import '../styles/globals.css';


 const AddHW: FC = () => {  

  return (
    <div className="min-h-screen bg-gray-50">
      
      <Header 
        TeacherName="Бладко Ю.В." 
        avatarSrc="zcz"/>
      
      <main className="container py-6 px-4">         
            <PageTitle
              text='Добавление домашнего задания'/>
              <div className="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow-sm border">
            <AddHWForm />
            </div>   
      </main>
    </div>
  );
};
export default AddHW