import org.usb4java.*;

import javax.usb.UsbDevice;

public class Main {

    public static void main(String[] args) {
        Context context = new Context();
        int result = LibUsb.init(context);
        if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to initialize libusb.", result);


        DeviceList list = new DeviceList();
        LibUsb.getDeviceList(context, list);

        for (int i=0; i<list.getSize(); i++) {
            try {
                DumpDevices.dumpConfigurationDescriptors(list.get(i), 100);
                DumpDevices.dumpDevice(list.get(i));
                System.out.println("\n");
            }
            catch (Exception e) {}
        }
        System.out.println("Devices: " + list.getSize());


    }

    static class EventHandlingThread extends Thread
    {
        /** If thread should abort. */
        private volatile boolean abort;

        /**
         * Aborts the event handling thread.
         */
        public void abort()
        {
            this.abort = true;
        }

        @Override
        public void run()
        {
            while (!this.abort)
            {
                int result = LibUsb.handleEventsTimeout(null, 250000);
                if (result != LibUsb.SUCCESS)
                    throw new LibUsbException("Unable to handle events", result);
            }
        }
    }
















    public static Device findDevice(short vendorId, short productId) {
        // Read the USB device list
        DeviceList list = new DeviceList();
        int result = LibUsb.getDeviceList(null, list);
        if (result < 0) throw new LibUsbException("Unable to get device list", result);

        try
        {
            // Iterate over all devices and scan for the right one
            for (Device device: list)
            {
                DeviceDescriptor descriptor = new DeviceDescriptor();
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to read device descriptor", result);
                if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
            }
        }
        finally
        {
            // Ensure the allocated device list is freed
            LibUsb.freeDeviceList(list, true);
        }

        // Device not found
        return null;
    }



}
