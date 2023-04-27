import { FormControl } from "@angular/forms";
import { environment } from "src/environments/environment";


//for validator
export function isPhoneValid(control: FormControl) {
    const regex: RegExp = /^[0-9]{3}-[0-9]{3}-?[0-9]{4}$/;
    if ((control.value !== "") && !regex.test(control.value)) {
        return { invalid: true };
    }
    return null;
}

export function isPhoneValueValid(value: string) {
    const regex: RegExp = /^[0-9]{3}-[0-9]{3}-?[0-9]{4}$/;
    if (!regex.test(value)) {
        return false;
    }
    return true;
}

export function isPasswordValid(control: FormControl) {
    const regex: RegExp = /^.{6}$/;
    if (!regex.test(control.value)) {
        return { invalid: true };
    }
    return null;
}
export function isPasswordValueValid(value: string) {
    const regex: RegExp = /^.{6}$/;
    if (!regex.test(value)) {
        return false;
    }
    return true;
}

export function isZipCodeValid(control: FormControl) {
    const regex: RegExp = /^[0-9]{5}$/;
    if (( control.value !== "") && !regex.test(control.value)) {
        return { invalid: true };
    }
    return null;
}

export function isZipCodeValueValid(control: FormControl) {
    const regex: RegExp = /^[0-9]{5}$/;
    if (!regex.test(control.value)) {
        return false;
    }
    return true;
}
export function toUrlPath(name: string) {
    if (name && name !== '')
        return environment.HTTP_SERVER + '/images/' + name;
    else return ''
}