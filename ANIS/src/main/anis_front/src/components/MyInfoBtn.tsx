import {useNavigate} from "react-router-dom";
import React from "react";
import {Button} from "@mui/material";

export default function MyInfoBtn() {
    const navigator = useNavigate();
return (
        <div>
            <Button onClick={() => navigator('/myInfo')}>내 정보 보기</Button>
        </div>
    );
}