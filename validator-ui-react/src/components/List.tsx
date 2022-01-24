import React from 'react'

interface Iprops{
        people:{
        name: string,
        age: number,
        url: string,
        note?:string
        }[]
    }

const List: React.FC<Iprops> = () => {

    return (
        <div>

            I am a List
            
        </div>
    )
}

export default List
