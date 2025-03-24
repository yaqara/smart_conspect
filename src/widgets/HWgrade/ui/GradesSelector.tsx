import {FC} from 'react';

interface GradesSelectorProps {
  selectedGrade:string|null;
  onSelectGrade:(grade: string)=>void;
}

export const GradesSelector:FC<GradesSelectorProps> = ({selectedGrade,onSelectGrade}) => {
  
  const grades = [
    {id:'excellent',label: 'Отлично',color:'83C77E'},
    {id:'good',label:'Хорошо',color:'E0E624'},
    {id:'satisfactory',label:'Удовлетворительно',color:'F5A958'},
    {id:'needsImprovement',label: 'Нужны исправления',color:'D75D5F'}
  ];

  return (
    <div className="mb-6">
      <h3 className="text-lg font-medium mb-3">Оцените работу ученика</h3>
      <div className="flex flex-wrap gap-2">
        {grades.map((grade) => (
          <button
          key={grade.id}
          className={`py-2 px-4 rounded-full border transition-colors ${
            selectedGrade === grade.id
                ?`bg-primary text-white border-primary hover:bg-blue-400`
                :'bg-white hover:bg-gray-100 border-gray-300 hover:border-gray-400'
          }`}

          onClick={() => onSelectGrade(grade.id)}>
        
          {grade.label}
        </button>
        ))}
      </div>
    </div>
  );
};
