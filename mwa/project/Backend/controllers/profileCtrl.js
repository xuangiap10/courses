
import userModel from "../models/userModel.js"
import bcrypt from 'bcrypt';

export async function getProfileById(req, res, next) {
    try {
        console.log('getProfileById')
        const { user_id } = req.params
        console.log(user_id);
        const result = await userModel.findOne(
            { _id: user_id },
            { items: 0, password: 0 }
        )
        res.json({ success: true, data: result })
    } catch (e) {
        next(e)
    }
}

export async function updateProfile(req, res, next) {
    try {
        const { user_id } = req.params
        const { fullname, address, phone } = req.body
        const result = await userModel.updateOne(
            { _id: user_id },
            {
                $set: {
                    fullname: fullname,
                    address: address,
                    phone: phone
                }
            }
        )
        res.json({ success: true, data: result })
    } catch (e) {
        next(e)
    }
}

export async function changePassword(req, res, next) {
    try {
        const { user_id } = req.params
        const { password, newpassword } = req.body
        //console.log(password)
        //console.log(newpassword)
        const user = await userModel.findOne({ _id: user_id }, { items: 0 });
        const match = await bcrypt.compare(password, user.password);
        if (!match) throw new Error('Incorrect Password')

        const hashed_password = await bcrypt.hash(newpassword, 10);
        //console.log(hashed_password)
        const result = await userModel.updateOne(
            { _id: user_id },
            { $set: { password: hashed_password } }
        )

        console.log(result)
        res.json({ success: true, data: result })
    } catch (e) {
        next(e)
    }
}