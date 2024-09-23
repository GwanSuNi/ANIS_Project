import Card from '@mui/material/Card';
import CardActionArea from '@mui/material/CardActionArea';
import CardContent from '@mui/material/CardContent';
import Chip from '@mui/material/Chip';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';

export default function SurveyCard({isComplete}: { isComplete: boolean }) {
    return (
        <Card elevation={7}>
            <CardActionArea>
                <CardContent component={Grid} container spacing={2} m={0} sx={{p: 1.5}}>
                    <Grid xs='auto'>
                        <Chip
                            label={isComplete ? '완료' : '미완료'}
                            color={isComplete ? 'success' : 'error'}
                            sx={{
                                fontSize: '20px',
                                width: '85px',
                                height: '100%'
                            }}
                        />
                    </Grid>
                    <Grid xs>
                        <Typography
                            variant='h5'
                            whiteSpace='nowrap'
                            overflow='hidden'
                            textOverflow='ellipsis'
                            py={.5}
                        >
                            설문조사 이름
                        </Typography>
                    </Grid>
                    <Grid xs='auto' display='flex' alignItems='center'>
                        <Typography variant='h6' color='text.secondary'>
                            설문조사 기간
                        </Typography>
                    </Grid>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}