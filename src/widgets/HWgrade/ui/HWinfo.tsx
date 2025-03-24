import { FC } from 'react';
import HWProps from '~/entities/HW/HWProps'
import StudentProps from '~/entities/Student/StudentProps';  

type HWInfoProps = {
  StudentName:string,
  submissionDate: string;
  theme: string;
  customTheme: string;
};
  export const HWinfo: FC<HWInfoProps> = ({StudentName,submissionDate,theme,customTheme}) => {
    return (
      <div className="max-w-3xl mx-auto my-8">
        <h1 className="text-2xl font-bold  mb-2">{StudentName}</h1>
        <p className="text-gray-600  mb-6">Дата отправки - {submissionDate}</p>       
        <div className="mt-8">
          <h1 className="text-xl font-bold mb-2">{theme}</h1>
          <p className="text-gray-600">{customTheme}</p>
        </div>
      </div>
    );
  };
  