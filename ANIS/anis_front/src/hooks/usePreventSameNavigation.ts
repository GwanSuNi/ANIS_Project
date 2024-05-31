import {useLocation, useNavigate} from 'react-router-dom';

const usePreventSameNavigation = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const preventSameNavigation = (newValue: string) => {
        if (location.pathname !== newValue)
            navigate(newValue);
    };

    return {preventSameNavigation};
};

export default usePreventSameNavigation;