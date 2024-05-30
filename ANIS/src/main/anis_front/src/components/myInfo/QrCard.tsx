import {UserInfo} from "./MyInfo";
import useQrCode from "../../hooks/useQrCode";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import * as React from "react";
import {Skeleton} from "@mui/material";

export default function QrCard({studentID}: { studentID: string }) {
    const {status, data, error} = useQrCode(studentID);

    return (
        <>
            {(!studentID || status === 'pending') &&
                <Skeleton variant="rectangular" width={300} height={300}/>
            }
            {status === 'error' &&
                <span>Error: {error.message}</span>
            }
            {status === 'success' &&
                <Card style={{
                    minWidth: '100px',
                    minHeight: '100px',
                    maxWidth: '300px',
                    maxHeight: '300px',
                    marginTop: 10
                }}>
                    <CardMedia
                        component="img"
                        src={data}
                        alt="qrCode"
                    />
                </Card>
            }
        </>
    )
}