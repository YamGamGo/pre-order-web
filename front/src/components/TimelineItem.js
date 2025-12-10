export default function TimelineItem({ year, title, description, image, reverse = false }) {
    return (
        <li className={reverse ? "timeline-inverted" : ""}>
            {image && (
                <div className="timeline-image">
                    <img className="rounded-circle img-fluid" src={image} alt={title} />
                </div>
            )}
            <div className="timeline-panel">
                <div className="timeline-heading">
                    {year && <h4 style={{ color: 'white' }}>{year}</h4>}
                    <h4 className="subheading" style={{ color: 'white' }}>{title}</h4>
                </div>
                <div className="timeline-body">
                    <p style={{ color: 'white' }}>{description}</p>
                </div>
            </div>
        </li>
    );
}