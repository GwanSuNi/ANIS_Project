import {useDispatch} from 'react-redux';
import {setQrInput} from '@redux';

export function useSerialPort() {
    const dispatch = useDispatch();

    const selectPort = async () => {
        if ('serial' in navigator) {
            try {
                const filters = [
                    {usbVendorId: 1504, usbProductId: 5889}, // 바코드 스캐너 정보
                ];

                // 사용자가 바코드 스캐너를 선택할 수 있게 프롬프트를 띄운다.
                // @ts-ignore
                const port = await navigator.serial.requestPort({filters});
                await port.open({baudRate: 11500});

                while (port.readable) {
                    const reader = port.readable.getReader();
                    try {
                        while (true) {
                            const {value, done} = await reader.read();
                            if (done) {
                                break;
                            }
                            const decoder = new TextDecoder('utf-8');
                            const text = decoder.decode(value).trim();
                            const trimmedText = String(parseInt(text, 10)); // 숫자로 변환해서 앞 0 제거 후 문자로 다시 변환
                            dispatch(setQrInput(trimmedText));
                        }
                    } catch (error) {
                        console.error(`An error occurred: ${error}`);
                    } finally {
                        reader.releaseLock();
                    }
                }
            } catch (error) {
                console.error(`An error occurred: ${error}`);
            }
        } else {
            console.error('Web Serial API is not supported in your browser.');
        }
    };

    return {selectPort};
}