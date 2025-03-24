'use client'
import {FC} from 'react';
import {Header} from '~/features/Header';
import { PageTitle } from '~/features/PageTitle';
import { ClassForm } from '~/widgets/AddClass/ui/ClassForm';
import '../styles/globals.css';

 const AddClass: FC = () => {  

  return (
    <div className="min-h-screen bg-gray-50">
      
      <Header 
        TeacherName="Бладко Ю.В." 
        avatarSrc="zcz"/>
      
      <main className="container py-6 px-4">         
            <PageTitle
              text='Добавление нового класса'/>
            <div className="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow-sm border">
            <ClassForm />
            </div>        
      </main>
    </div>
  );
};
export default AddClass