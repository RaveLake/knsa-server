package knu.notice.knunoticeserver.controller

import knu.notice.knunoticeserver.dto.DeviceDTO
import knu.notice.knunoticeserver.dto.UserDTO
import knu.notice.knunoticeserver.service.DeviceService
import knu.notice.knunoticeserver.service.UserService
import knu.notice.knunoticeserver.util.getLogger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val userService: UserService, private val deviceService: DeviceService,
) {

    @GetMapping("/device")
    fun getDevice(@RequestParam("id") id: String): DeviceDTO {
        return deviceService.getDeviceById(id)
    }

    @PostMapping("/device")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun postDevice(@RequestBody newDevice: DeviceDTO): DeviceDTO {
        return deviceService.saveNewDevice(newDevice)
    }

    @PutMapping("/device")
    fun putDevice(@RequestBody updateDevice: DeviceDTO): DeviceDTO {
        return deviceService.updateDevice(updateDevice)
    }

    @DeleteMapping("/device")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteDeviceByOne(@RequestBody deleteDevice: DeviceDTO) {
        deviceService.deleteDeviceById(deleteDevice.id)
    }

    @GetMapping("/user")
    fun getUser(@RequestParam("id") id: String): UserDTO {
        return userService.getUserById(id)
    }

    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun postUser(@RequestBody newUser: UserDTO): UserDTO {
        return userService.saveNewUser(newUser)
    }

    @PutMapping("/user")
    fun putUser(@RequestBody updateUser: UserDTO): UserDTO {
        return userService.updateUser(updateUser)
    }

    @DeleteMapping("/user")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteUser(@RequestBody deleteUser: UserDTO) {
        userService.deleteUserById(deleteUser)
    }
}