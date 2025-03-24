'use client'
import { FC } from "react";
import { Button } from "~/components/ui/button";
import Image from "next/image";
import image1 from '~/shared/ui/image1.png';
import { useRouter } from "next/navigation";
import '../styles/globals.css';
const Index: FC = () => {
  const router = useRouter();

  return (
    <div className="bg-gray-50 text-white min-h-screen flex items-center">
      <div className=" bg-blue-600 max-w-screen-xl mx-auto p-6 md:p-12 flex flex-col md:flex-row gap-8 rounded-2xl">

        <div className="flex-1">
          <h1 className="text-5xl font-bold mb-6">
            Новый подход к образованию детей
          </h1>
          <p className="text-lg mb-8">
            Наш сайт предоставляет возможности для сбора записей по предметам у учеников и их проверки. 
            Это позволит вам экономить время при проверке конспектов и других работ и вести учёт выполненных домашних заданий. 
            С помощью обратной связи вы с лёгкостью можете указать на недочёты, допущенные детьми в их работах, а также оценить их по привычной системе оценивания. 
            Просто попробуйте!
          </p>
          <div className="flex gap-4 flex-col md:flex-row">
            <Button 
              onClick={() => router.push("/SignIn")}
              className="bg-sky-950 hover:bg-teal-900 px-8 py-3 rounded-lg">
              Войти
            </Button>
            <Button 
              onClick={() => router.push("/SignUp")}
              className="bg-sky-950 hover:bg-teal-900 px-8 py-3 rounded-lg">
              Зарегистрироваться
            </Button>
          </div>
        </div>

        <div className="flex-1 flex justify-center items-center">
          <Image
            src={image1}
            alt="Образовательная платформа"
            width={500}
            height={300}
            className="w-full h-auto"/>
        </div>
      </div>
    </div>
  );
};
export default Index