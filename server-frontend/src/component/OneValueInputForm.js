function OneValueInputForm(props) {
    return (
        <div>
            <form onSubmit={props.submitHandler}>
                <div className="new-item-controls" style={{ width: "500px" }}>
                    <div className="new-item-control">
                        <h3>{props.formTitle}</h3>
                    </div>
                    <div className="new-item-control">
                        <label>{props.inputName}</label>
                        <input type="text" value={props.inputValue} name="queryMessage" onChange={props.inputChangeHandler} />
                    </div>
                    <div className="new-item-control">
                        <button type="submit">Submit</button>
                        <button onClick={props.visibility}>Cancel</button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default OneValueInputForm;