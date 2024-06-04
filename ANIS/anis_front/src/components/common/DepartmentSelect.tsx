import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, {SelectChangeEvent} from '@mui/material/Select';
import {useEffect, useState} from 'react';
import axios from 'axios';

export default function DepartmentSelect({selectedDepIndex, setSelectedDepIndex}: {
    selectedDepIndex: string,
    setSelectedDepIndex: (value: string) => void
}) {
    const [departments, setDepartments] = useState<{ depIndex: number, depName: string }[]>([]);

    useEffect(() => {
        const fetchDepartments = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/departments');
                setDepartments(response.data);
                console.log(response.data);
            } catch (error) {
                console.error('Failed to fetch departments', error);
            }
        };
        fetchDepartments();
    }, []);


    const handleChange = (event: SelectChangeEvent) => {
        setSelectedDepIndex(event.target.value);
    };

    return (
        <div>
            <FormControl required sx={{minWidth: 190}}>
                <InputLabel id="demo-simple-select-helper-label">학과</InputLabel>
                <Select
                    required
                    labelId="demo-simple-select-helper-label"
                    id="demo-simple-select-helper"
                    value={selectedDepIndex}
                    label="Age"
                    onChange={handleChange}
                    fullWidth
                >
                    {departments.map((department) => (
                        <MenuItem key={department.depIndex} value={department.depIndex}>
                            {department.depName}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
        </div>
    );
}