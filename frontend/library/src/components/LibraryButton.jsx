export default function LibraryButton({buttonAction, buttonLabel}) {
    return (
        <div>
            <button onClick={buttonAction}>{buttonLabel}</button>
        </div>
    )
}