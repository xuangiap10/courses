import usersModel from '../models/userModel.js'
import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';

export async function login(req, res, next) {

    try {
        const { email, password } = req.body;
        const user = await usersModel.findOne({ email }, { items: 0 }).lean();
        if (user) {
            const match = await bcrypt.compare(password, user.password);
            if (match) {
                const JWT_Token = jwt.sign({
                    ...user,
                    password: ''
                }, process.env.SECRET_KEY);
                res.json({ success: true, data: JWT_Token });
            } else {
                next(new Error('wrong password'));
            }
        } else {
            next(new Error('User not found'));
        }

    } catch (error) {
        next(error);
    }
}

export async function signup(req, res, next) {

    try {
        const new_user = req.body;
        const { password: plain_password } = new_user;
        const hashed_password = await bcrypt.hash(plain_password, 10);
        const result = await usersModel.create({
            ...new_user,
            password: hashed_password
        });
        res.json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}

export async function getAll(req, res, next) {
    try {
        const result = await usersModel.find({});
        res.json({ success: true, data: result })
    } catch (error) {
        next(error);
    }
}

export async function getById(req, res, next) {
    try {
        const { user_id } = req.params;
        const result = await usersModel.findOne({ _id: user_id });
        res.json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}

export async function deleteById(req, res, next) {
    try {
        const { user_id } = req.params;
        const result = await usersModel.deleteOne({ _id: user_id });
        res.json({ success: true, data: result })
    } catch (error) {
        next(error);
    }
}
