import CardActionArea from "@mui/material/CardActionArea";
import {Skeleton} from "@mui/material";
import CardMedia from "@mui/material/CardMedia";
import Card from "@mui/material/Card";
import * as React from "react";
import useProfileImage from "../../hooks/useProfileImage";
import {UserInfo} from "./MyInfo";

export default function ImageCard({userInfo, handleUploadClick}: {
    userInfo: UserInfo,
    handleUploadClick?: (studentID: string) => void
}) {
    const {status, data, error, isFetching} = useProfileImage(userInfo?.studentID as string);
    return (
        <Card
            style={{width: '203px', height: '260px'}}>
            <CardActionArea onClick={() => {
                if (handleUploadClick) handleUploadClick(userInfo.studentID);
            }}>
                {(!userInfo.studentID || status === 'pending') ? (
                        <Skeleton variant="rectangular" width={203} height={260}/>)
                    : status === 'error' ? (
                            <CardMedia
                                component="img"
                                image={'https://via.placeholder.com/203x260.png?text=No+Image'}
                                alt="profile"
                            />
                        ) : (
                            <CardMedia
                                component="img"
                                image={data}
                                alt="profile"
                            />
                        )}
            </CardActionArea>
        </Card>
    )
}