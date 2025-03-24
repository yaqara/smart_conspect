'use client'
import {FC} from 'react';
import {Header} from '~/features/Header';
import { PageTitle } from '~/features/PageTitle';
import { Classes } from '~/widgets/ClassList/ui/Classes';
import '../styles/globals.css';
const ClassList: FC = () => {  

  return (
    <div className="min-h-screen bg-gray-50">
      
      <Header 
        TeacherName="Бладко Ю.В." 
        avatarSrc="zcz"/>
      
      <main className="container py-6 px-4">         
            <PageTitle
              text='Мои классы'/>
            <div className="max-w-15xl mx-auto  p-6">
            <Classes/>
            </div>        
      </main>
    </div>
  );
};
export default ClassList