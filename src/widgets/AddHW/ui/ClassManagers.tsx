"use client"
import * as React from "react"
import { Button } from "~/components/ui/button"
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "~/components/ui/command"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "~/components/ui/popover"

const availableClasses = [
  {
    value: "10A IT",
    label: "10A Информатика",
  },
  {
    value: "10B Math",
    label: "10Б Математика",
  },
  {
    value: "9G Phys",
    label: "9Г Физика",
  },
  {
    value: "8.1. Math",
    label: "8.1. Математика",
  },
]

type ComboboxDemoProps = {
  selectedClasses: string[]
  onClassesChange: (classes: string[]) => void
  hasError?: boolean
}

export const ComboboxDemo = ({
  selectedClasses,
  onClassesChange,
  hasError = false,
}: ComboboxDemoProps) => {
  const [open, setOpen] = React.useState(false)

  const handleSelect = (value: string) => {
    if (!selectedClasses.includes(value)) {
      onClassesChange([...selectedClasses, value])
    }
    setOpen(false)
  }

  const handleRemove = (value: string) => {
    onClassesChange(selectedClasses.filter((cls) => cls !== value))
  }

  return (
    <div className="space-y-2">
      <Popover open={open} onOpenChange={setOpen}>
        <PopoverTrigger asChild>
          <Button
            variant="outline"
            className={`
              w-full justify-center 
              ${hasError ? "border-red-500 focus:border-red-500" : ""}
            `}
          >
            {selectedClasses.length > 0
              ? "+ Добавить класс"
              : "Выбрать класс"}
          </Button>
        </PopoverTrigger>
        <PopoverContent className="p-0 w-72">
          <Command>
            <CommandInput placeholder="Поиск класса..." />
            <CommandList>
              <CommandEmpty>Класс не найден</CommandEmpty>
              <CommandGroup>
                {availableClasses.map((cls) => (
                  <CommandItem
                    key={cls.value}
                    value={cls.value}
                    onSelect={() => handleSelect(cls.value)}
                  >
                    {cls.label}
                  </CommandItem>
                ))}
              </CommandGroup>
            </CommandList>
          </Command>
        </PopoverContent>
      </Popover>

      {selectedClasses.length > 0 && (
        <div className="flex flex-wrap gap-2">
          {selectedClasses.map((clsValue) => {
            const cls = availableClasses.find((c) => c.value === clsValue)
            return (
              <span
                key={clsValue}
                className={`
                  inline-flex items-center px-3 py-1 text-xs font-semibold 
                  rounded-full
                  ${hasError 
                    ? "bg-red-100 text-red-700 dark:bg-red-900 dark:text-red-300" 
                    : "bg-gray-200 dark:bg-gray-700 dark:text-white"
                  }
                `}
              >
                {cls?.label || clsValue}
                <button
                  type="button"
                  onClick={() => handleRemove(clsValue)}
                  className="ml-1 text-red-500 hover:text-red-700"
                >
                  ×
                </button>
              </span>
            )
          })}
        </div>
      )}
    </div>
  )
}