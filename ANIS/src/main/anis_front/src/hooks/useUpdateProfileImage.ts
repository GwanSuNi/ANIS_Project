import { useMutation, useQueryClient } from '@tanstack/react-query';
import {secInstance} from "@utils";
import {AxiosResponse} from "axios";

interface UpdateProfileImageVariables {
    studentID: string;
    file: File;
}

const updateProfileImage = async ({ studentID, file }: UpdateProfileImageVariables) => {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('studentId', studentID);

    try {
        const response: AxiosResponse = await secInstance.put('/api/image/', formData);
        return response.data;
    } catch (error: any) {
        if (error.response && error.response.status === 417) {
            alert(error.response.data);
        }
        // 오류가 발생한 경우 undefined를 반환합니다.
        return undefined;
    }
};

const useUpdateProfileImage = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: updateProfileImage,
        onSuccess: (data, variables) => {
            queryClient.invalidateQueries({
                queryKey: ['profileImage', variables.studentID],
            });
        },
    });
};

export default useUpdateProfileImage;