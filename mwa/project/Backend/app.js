//1. deps
import express, { json } from 'express'
import dotenv from 'dotenv'
import morgan from 'morgan'
import cors from 'cors'
import mongoose from 'mongoose'
import path from 'path'
import url from 'url'

import usersRouter from './routers/usersRouter.js'
import searchRouter from './routers/searchRouter.js'

//2. instance
const app = express()

//3. config
dotenv.config()
app.disable('x-powered-by')

try {
    await mongoose.connect(process.env.DB_CONNECTION);
    console.log('connect mongodb successfully ')
} catch (e) {
    console.log('connect mongodb failed ' + e.message)
}

//const __dirname = path.dirname(new URL(import.meta.url).pathname);
const __dirname = url.fileURLToPath(new URL('.', import.meta.url));
//4. middlewares
app.use(cors())
app.use(morgan('dev'))
app.use(json())

app.use('/images', express.static(path.join(__dirname, 'assets/pics')));

//5. routers
app.use('/users', usersRouter);
app.use('/search', searchRouter);

app.all('*', (req, res, next) => {
    next(new Error('Router not found'))
})
//6. error handling
app.use(function (err, req, res, next) {
    res.status(500).json({ success: false, message: err.message });
})

//7. boot up
app.listen(3000, () => console.log('Server started at port 3000 .....'))
