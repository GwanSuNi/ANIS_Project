import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import CircularProgressWithLabel from '../components/common/CircularProgressWithLabel';
import {SurveySection} from '../components/survey';

export default function SurveyListPage() {
    return (
        <Container maxWidth='lg'>
            <Box display='flex' justifyContent='space-between' mb={8}>
                <Typography variant='h3' fontWeight='bold' gutterBottom>
                    설문조사
                </Typography>
                <Stack direction='row' justifyContent='flex-end' alignItems='center' spacing={2}>
                    <Typography variant='h4'>
                        진행률
                    </Typography>
                    <CircularProgressWithLabel value={100}/>
                </Stack>
            </Box>
            <SurveySection isComplete={false} count={5}/>
            <SurveySection isComplete={true} count={5}/>
        </Container>
    );
}