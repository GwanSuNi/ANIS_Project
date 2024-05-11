import React, { useState, useRef } from 'react';
import { useZxing, DecodeHintType, Result } from 'react-zxing';
import {Box, Button} from "@mui/material";

function BarcodeScanner() {
    const [result, setResult] = useState<string | null>(null);
    const [isPaused, setIsPaused] = useState<boolean>(false);
    const { ref } = useZxing({
        onDecodeResult: (result: Result) => {
            setResult(result.getText());
        },
        paused: isPaused,
    });

    const togglePause = () => {
        setIsPaused(!isPaused);
    };

    return (
        <Box sx={{ width:'100%', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>
            <video width={"400px"} height={"300px"} ref={ref} />
            <p>Last result: {result}</p>
            <Button variant="contained" onClick={togglePause}>
                {isPaused ? 'Resume Scanning' : 'Pause Scanning'}
            </Button>
        </Box>
    );
}

export default BarcodeScanner;