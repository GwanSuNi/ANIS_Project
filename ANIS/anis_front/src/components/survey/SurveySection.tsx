import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Divider from '@mui/material/Divider';
import SurveyCard from './SurveyCard';

export default function SurveySection({isComplete, count}: { isComplete: boolean, count: number }) {
    return (
        <Box>
            <Typography variant='h4' fontFamily='NanumGothicBold' gutterBottom>
                {isComplete ? '완료된' : '미완료'} 설문조사
            </Typography>
            <Typography variant='h6' color='text.secondary'>
                <Typography
                    component='span'
                    fontSize={30}
                    fontFamily='NanumGothicBold'
                    color='error.light'
                    fontWeight='bold'
                >
                    {count}
                </Typography>
                {isComplete ? '건의 설문조사를 하셨습니다.' : '건의 설문조사가 있습니다.'}
            </Typography>
            <Divider sx={{borderWidth: 1, mt: 1.5}}/>
            <Stack
                direction='column'
                justifyContent='flex-start'
                alignItems='stretch'
                spacing={3}
                p={2}
                mb={7}
            >
                {[...Array(count)].map((_, index) => (
                    <SurveyCard key={index} isComplete={isComplete}/>
                ))}
            </Stack>
        </Box>
    );
}