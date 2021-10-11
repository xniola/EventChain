const Navbar = () => {
    return (
      <nav className="navbar">
        <h1>EventChain</h1>
        {/* Se vogliamo dei tasti statici in ogni finestra in alto a destra della navbar
        <div className="links">
          <a href="/">Home</a>
          <a href="/events" style={{ 
            color: 'white', 
            backgroundColor: '#1392b9',
            borderRadius: '8px' 
          }}>Go to events!</a>
        </div>
        */}
      </nav>
    );
  }
   
  export default Navbar;