import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import CircularProgressWithLabel from '../components/common/CircularProgressWithLabel';
import {SurveySection} from '../components/survey';

export default function SurveyListPage() {
    //TODO: 서버로부터 데이터 받아와야 함
    const assessmentCount = 10; // 총 진단평가 개수
    const completeCount = 4; // 완료된 진단평가 개수
    const incompleteCount = 6; // 미완료된 진단평가 개수

    return (
        <Container maxWidth='lg'>
            <Box display='flex' justifyContent='space-between' mb={8}>
                <Typography variant='h3' fontWeight='bold' gutterBottom>
                    설문조사 · 진단평가
                </Typography>
                <Stack direction='row' justifyContent='flex-end' alignItems='center' spacing={2}>
                    <Typography variant='h4'>
                        진행률
                    </Typography>
                    <CircularProgressWithLabel value={completeCount / assessmentCount * 100}/>
                </Stack>
            </Box>
            <SurveySection isComplete={false} count={incompleteCount}/>
            <SurveySection isComplete={true} count={completeCount}/>
        </Container>
    );
}