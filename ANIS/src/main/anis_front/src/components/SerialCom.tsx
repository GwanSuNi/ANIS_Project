import {useSerialPort} from "../hooks/useSerialPort";
import {RootState} from "../redux/store";
import {useSelector} from "react-redux";

export default function SerialCom() {
    const { selectPort } = useSerialPort();
    const qrInput = useSelector((state:RootState) => state.qrInput.qrInput);
    return (
        <div>
            <button onTouchStart={selectPort} onClick={selectPort}>Connect to Serial Port</button>
            <h1>{qrInput}</h1>
        </div>
    );
}