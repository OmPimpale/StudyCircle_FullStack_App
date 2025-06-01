import React from 'react';


function ResourcesPage() {
  return (
    <>
      <Navbar />
      <div className="resources-container">
        <h1>Resources and Learning Materials</h1>
        <p>Explore a variety of resources and learning materials categorized by subject.</p>

        {/* Placeholder for resources */}
        <div className="resource-list">
          <h2>Mathematics</h2>
          <ul>
            <li>Placeholder for Math Resource 1</li>
            <li>Placeholder for Math Resource 2</li>
            {/* Add more math resources */}
          </ul>

          <h2>Science</h2>
          <ul>
            <li>Placeholder for Science Resource 1</li>
            <li>Placeholder for Science Resource 2</li>
            {/* Add more science resources */}
          </ul>

          <h2>History</h2>
          <ul>
            <li>Placeholder for History Resource 1</li>
            <li>Placeholder for History Resource 2</li>
            {/* Add more history resources */}
          </ul>

          {/* Add more subjects and their resources */}
        </div>
      </div>
      <Footer />
    </>
  );
}

export default ResourcesPage;