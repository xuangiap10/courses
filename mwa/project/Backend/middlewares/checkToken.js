import jwt from 'jsonwebtoken';

export default function (req, res, next) {

    try {

        const header = req.headers['authorization'];
        const token = header.split(' ')[1];
        if (token) {
            const decoded_token = jwt.verify(token, process.env.SECRET_KEY);
            if (decoded_token) {
                req.token = decoded_token;
                next()
            } else {
                next(new Error('Invalid token'));
            }
        } else {
            next(new Error('Token not found'));
        }
    } catch (error) {
        next(error);
    }
}