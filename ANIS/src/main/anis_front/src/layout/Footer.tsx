import {ChangeEvent, cloneElement} from 'react';
import {useLocation} from 'react-router-dom';
import Paper from '@mui/material/Paper';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import PeopleIcon from '@mui/icons-material/People';
import HomeIcon from '@mui/icons-material/Home';
import PersonIcon from '@mui/icons-material/Person';
import {usePreventSameNavigation} from '@hooks';

export default function Footer() {
    const location = useLocation();
    const {preventSameNavigation} = usePreventSameNavigation();

    const navigationItems = [
        {label: '친구', value: '/friend', icon: <PeopleIcon/>},
        {label: '홈', value: '/', icon: <HomeIcon/>},
        {label: '내정보', value: '/myInfo', icon: <PersonIcon/>},
    ];

    const handleChange = (_: ChangeEvent<{}>, newValue: string) => {
        preventSameNavigation(newValue);
    };

    return (
        <Paper sx={{position: 'sticky', bottom: 0, left: 0, right: 0}} elevation={3}>
            <BottomNavigation
                showLabels
                value={location.pathname}
                onChange={handleChange}
                sx={{height: '67px'}}
            >
                {navigationItems.map(item => (
                    <BottomNavigationAction
                        key={item.label}
                        label={item.label}
                        value={item.value}
                        sx={{maxWidth: 'none', py: 1}}
                        icon={cloneElement(item.icon, {
                            fontSize: 'large',
                            color: location.pathname === item.value ? 'secondary' : 'inherit'
                        })}
                    />
                ))}
            </BottomNavigation>
        </Paper>
    );
}