import subprocess
import time

DEVICE_SERIAL = "3dd2f6c2"

GUANGDONG_LOCATIONS = {
    "广州塔": (23.105925, 113.324520),
    "深圳市民中心": (22.548470, 114.060250),
    "珠海长隆": (22.110880, 113.537040),
}

def run_adb(cmd_list):
    return subprocess.run(cmd_list, capture_output=True, text=True, encoding='utf-8')

def set_location(lat, lon):
    print(f"设置位置: {lat:.6f}, {lon:.6f}")
    cmd = ["adb", "-s", DEVICE_SERIAL, "shell", "am", "broadcast",
           "-a", "com.example.mocklocation.SET_LOCATION",
           "--ed", "lat", str(lat), "--ed", "lon", str(lon)]
    result = run_adb(cmd)
    print("已发送" if result.returncode == 0 else f"失败: {result.stderr}")

def goto(name):
    if name in GUANGDONG_LOCATIONS:
        lat, lon = GUANGDONG_LOCATIONS[name]
        print(f"前往: {name}")
        set_location(lat, lon)

def main():
    cmd = ["adb", "-s", DEVICE_SERIAL, "get-state"]
    if "device" not in run_adb(cmd).stdout.lower():
        print("设备未连接")
        return
    print("设备已连接")
    
    while True:
        print("\n1. 广州塔\n2. 深圳市民中心\n3. 珠海长隆\n0. 退出")
        choice = input("选择: ").strip()
        if choice == "0":
            break
        elif choice.isdigit():
            idx = int(choice) - 1
            locs = list(GUANGDONG_LOCATIONS.keys())
            if 0 <= idx < len(locs):
                goto(locs[idx])

if __name__ == "__main__":
    main()
