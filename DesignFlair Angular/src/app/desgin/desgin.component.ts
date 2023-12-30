import { Component } from '@angular/core';
import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-desgin',
  templateUrl: './desgin.component.html',
  styleUrls: ['./desgin.component.css']
})
export class DesginComponent {

  roomList: string[] = [
    "Living Room",
    "Dining Room",
    "Gaming Room",
    "Bedroom",
    "Bathroom",
    "Office",
    "Kitchen",
    "Guest Room",
    "Laundry Room",
    "Home Theater",
    "Playroom",
    "Music Room",
    "Exercise Room",
    "Library",
    "Sunroom",
    "Mudroom",
    "Attic",
    "Basement",
    "Pantry",
    "Wine Cellar",
    "Garage",
    "Outdoor Living Space",
    "Pool Room",
    "Study Room",
    "Home Office",
    "House Exterior",
    "Outdoor Pool Area",
    "Outdoor Patio",
    "Outdoor Garden",
    "Meeting Room",
    "Workshop",
    "Fitness Gym",
    "Coffee Shop",
    "Clothing Store",
    "Walk-in Closet",
    "Toilet",
    "Restaurant",
    "Coworking Space",
    "Hotel Lobby",
    "Hotel Room",
    "Hotel Bathroom",
    "Exhibition Space",
    "Onsen",
    "Drop Zone"
  ];

  designStyleList: string[] = [
    "Christmas",
    "Modern",
    "Neutral",
    "Monochromatic",
    "Complementary",
    "Cyberpunk",
    "Analogous",
    "Warm",
    "Cool",
    "Pastel",
    "Black and white",
    "Earthy",
    "Vintage",
    "Minimalist",
    "Scandinavian",
    "Bohemian",
    "High-Contrast",
    "Bright",
    "Ocean-inspired",
    "Rustic",
    "Tropical",
    "Bold",
    "Jewel-toned",
    "Art Deco",
    "Mediterranean",
    "Traditional",
    "Beachy",
    "Spanish",
    "Swedish",
    "Boho",
    "Victorian gothic",
    "Organic modern",
    "Dark academia",
    "Modern rustic",
    "Art deco modern",
    "Industrial chic",
    "Industrial rustic",
    "Rustic",
    "Industrial",
    "Eclectic",
    "Victorian",
    "Luxury",
    "Gothic",
    "Moroccan",
    "French",
    "Mexican"
  ];
  
  selectedRoom: string = '';
  selectedDesign: string = '';
  text: string = '';
  image: string = '';
  upscaled_image: string = '';
  imageData: any;
loading: boolean = false;

  constructor(private apiService: ApiService, private router: Router) {}

  onRoomSelect(room: string) {
    this.selectedRoom = room;
    console.log('Selected room:', this.selectedRoom);
    // You can perform further actions with the selected room here
  }

  onDesignSelect(design: string) {
    this.selectedDesign = design;
  }

  onSend() {
    this.image = ""
    this.upscaled_image = ""
    this.loading = true;
    let prompt = "A 4k design of " + this.selectedRoom + ", " + this.selectedDesign + " " + this.text
    console.log("Prompt: " + prompt);
    
    this.apiService.generateImage(prompt).subscribe({
      next: (v) => this.handleImage(v),
      error: (e) => this.loading = false,
      complete: () => this.loading = false
    })
  }
  handleImage(v: any): void {
    console.log(v);
    this.imageData = v;
    this.image = "data:image/png;base64," + v.data
  }

  onUpscale() {
    this.loading = true;
    this.apiService.upscaleImage(this.imageData).subscribe({
      next: (v) => this.handleScaledImage(v),
      error: (e) => this.loading = false,
      complete: () => this.loading = false
    })
  }

  handleScaledImage(v: any): void {
    console.log(v);
    this.upscaled_image = "data:image/png;base64," + v.data
  }

}
