import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Divider from '@mui/material/Divider';
import SurveyCard from './SurveyCard';

export default function SurveySection({isComplete, count}: { isComplete: boolean, count: number }) {
    return (
        <>
            <Typography variant='h4' fontFamily='NanumGothicBold' gutterBottom>
                {isComplete ? '완료된' : '미완료'} 설문조사
            </Typography>
            <Stack
                direction="row"
                justifyContent="flex-start"
                alignItems="flex-end"
                spacing={0.5}
            >
                <Typography fontSize={30} fontFamily='NanumGothicBold' color='error.light' fontWeight='bold'>{count}</Typography>
                <Typography variant='h6' color='text.secondary' pb='3px'>
                    {isComplete ? '건의 설문조사를 하셨습니다.' : '건의 설문조사가 있습니다.'}
                </Typography>
            </Stack>
            <Divider sx={{borderWidth: 1, my: 2}}/>
            <Stack
                direction='column'
                justifyContent='flex-start'
                alignItems='stretch'
                spacing={5}
                p={2}
                mb={10}
            >
                {[...Array(count)].map((_, index) => (
                    <SurveyCard key={index} isComplete={isComplete}/>
                ))}
            </Stack>
        </>
    );
}