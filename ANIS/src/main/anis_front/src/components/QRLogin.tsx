import React from "react";
import {Box, Button, Link, Typography} from "@mui/material";
import QrReader from "./QrReader";

export default function QRLogin() {
    return (
        <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>
            <Box  height={400}
                  width={400}
                  my={4}
                  display="flex"
                  alignItems="center"
                  gap={4}
                  p={2}
                  sx={{ border: '2px solid grey' }}>
                <QrReader/>
            </Box>

            <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>
                <Typography variant="h3" component="h2">
                    모바일 학생증 QR 코드
                </Typography>
                <br/>
                <Typography variant="h3" component="h2">
                    또는
                </Typography>
                <br/>
                <Typography variant="h3" component="h2">
                    학생증 바코드를 스캔해주세요.
                </Typography>
            </Box>
            <Button variant="contained">인식에 문제가 있나요?</Button>

            <Link href="#" variant="body2" sx={{color: 'gray'}}>
                {'학교 관계자이신가요?'}
            </Link>
        </Box>
    )


}